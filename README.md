# RequiredPlugins

This plugin allows you to automatically shut down your server if specified plugins fail to load at startup.

Config File:
Create `RequiredPlugins/config.yml` within the `plugins` directory.
This file should contain `required-plugins: "plugin-name,second-required-plugin"`

This project has minimal testing and I give no guarantees of reliability or ease of use.
Though I expect it to use a simple enough check to work indefinitely...

Compiling:
* git clone https://github.com/chickeneer/RequiredPlugins
* mvn install