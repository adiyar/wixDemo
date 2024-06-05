#!/bin/bash

#
# BlueVine Environment Bootstrap Preflight Script
# This script is used to prepare the environment for the bootstrap.rb script which will continue setting up the developer environment
# Because of the fact that a fresh installation of OSX does not contain runtime dependencies, this script purpose is to make sure the runtime dependencies are set
# as expected
#
# CAUTION:: This script can run only from within BlueVine offices networks and might require VPN client in order to work properly
#
ֿ
# Last known (supported) version of XCode which is compatible with BlueVine OSX systems
LAST_KNOWN_SUPPORTED_XCODE_VERSION="12.5.1"
# Chef Solo repository location on the operation system, Chef solo is the handler which bootstraps the system further after the bootstrap script prepares it
CHEF_SOLO_REPO="$HOME/bluevine/sources/chef-solo-repo"
echo '* Bluevine Environment Bootstrap Preflight *'
echo "Welcome to the BlueVine environment preflight script"
echo "Please make sure you are connected to the office network and have a stable network connection"
echo "Preparing system for BV environment bootstrap.."
current_version=$(sw_vers -productVersion | cut -d '.' -f1) # The current version of the MacOS in MacBook
supported_version="11" # The MacOS version that support the Xcode from Internet and not from S3
echo "Checking OSX compability.."
echo "XCODE metadata: $current_version"
if ! [ -d "$(xcode-select -p)" ]; then
  echo "XCode (OSX Developer Tools) is not installed on this OSX. Installing."
  if [ "$current_version" -lt "$supported_version" ]; then
    curl https://s3.amazonaws.com/bv-dev-laptop-setup/Command_Line_Tools_for_Xcode_11.5.dmg >/tmp/Command_Line_Tools_for_Xcode_11.5.dmg
    hdiutil attach /tmp/Command_Line_Tools_for_Xcode_11.5.dmg
    cd "/Volumes/Command Line Developer Tools" || exit 2
    sudo installer -pkg "./Command Line Tools.pkg" -target /
  else
    curl "https://s3.amazonaws.com/bv-dev-laptop-setup/Command_Line_Tools_for_Xcode_$LAST_KNOWN_SUPPORTED_XCODE_VERSION.dmg" >/"tmp/Command_Line_Tools_for_Xcode_$LAST_KNOWN_SUPPORTED_XCODE_VERSION.dmg"
    hdiutil attach "/tmp/Command_Line_Tools_for_Xcode_$LAST_KNOWN_SUPPORTED_XCODE_VERSION.dmg"
    cd "/Volumes/Command Line Developer Tools" || exit 2
    sudo installer -pkg "./Command Line Tools.pkg" -target /
  fi
fi

# Check if we are running an instance of the bootstrap to take care of Catalina upgrade
if [[ -n "$BOOTSTRAP_CATALINA_UPGRADE" ]]; then
    echo "Bootstrap mode is set to upgrade from Catalina, \n1.your Python virtual environment will be recreated\n2.Python will be replaced with Python 3.9\n3.OSX Development tools (XCODE) will get reinstalled"
    echo "Reinstalling XCode.."
    rm -rf ~/bluevine/sources/chef-solo-repo || true
    xcode-select --install
fi

if [[ $(which ruby) =~ 'rvm' ]]; then
  echo 'RVM based ruby paths detected.'
  echo 'The Bootstrap should run from your system Ruby installation at /usr/bin/ruby'
  echo 'Please type the command: "rvm use system" then run the bootstrap script again.'
  echo 'Exiting'
  exit 1
fi

if [ -d "$CHEF_SOLO_REPO" ]; then
  echo "Bootstrap repository found at $CHEF_SOLO_REPO"
  cd "$HOME/bluevine/sources/chef-solo-repo" || exit 2
  git reset --hard
  git checkout master
  git pull
else
  echo "Bootstrap repository not found at $CHEF_SOLO_REPO, Getting latest updates from AWS s3"
  mkdir -p "$HOME/bluevine/sources" && cd "$HOME/bluevine/sources" && mkdir chef-solo-repo
  cd chef-solo-repo || exit 2
  declare -a arr=("bootstrap.rb" "Gemfile.lock" "Gemfile")
  for i in "${arr[@]}"; do
    curl "https://s3.amazonaws.com/bv-dev-laptop-setup/$i" >"$i"
  done
fi
echo 'Validating shell is set to BASH'
if [ "$SHELL" == "/bin/bash" ]; then
  echo "Shell is already set to bash"
else
  echo "Changing shell to Bash, please input your system password if asked to."
  chsh -s /bin/bash
fi
if test -f "./bootstrap.rb"; then
  echo "Bootstrap executable found, moving forward."
  echo "Validating Bootstrap dependencies.."
  echo "Please allow sudo access if asked to."
  sudo gem install bundler -v 2.0.2 --silent
  sudo bundle config build.nokogiri --use-system-libraries
  echo "Installing dependencies, please wait.."
  sudo bundle install --quiet
  /usr/bin/ruby ./bootstrap.rb
fi

