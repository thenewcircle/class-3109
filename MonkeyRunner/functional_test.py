# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice

# Connects to the current device, returning a MonkeyDevice object
device = MonkeyRunner.waitForConnection()

# Installs the Android package. Notice that this method returns a boolean, so you can test
# to see if the installation worked.
device.installPackage('../ContentBrowser/bin/ContentBrowser.apk')

# sets a variable with the package's internal name
package = 'com.cisco.contentbrowser'

# sets a variable with the name of an Activity in the package
activity = 'com.cisco.contentbrowser.MainActivity'

# sets the name of the component to start
runComponent = package + '/' + activity

# Runs the component
device.startActivity(component=runComponent)

# Takes a screenshot
result = device.takeSnapshot()

# Writes the screenshot to a file
result.writeToFile('startup.png','png')

# Presses the Menu button
device.touch(150, 300, MonkeyDevice.DOWN_AND_UP)
device.press('KEYCODE_T', MonkeyDevice.DOWN_AND_UP)
device.press('KEYCODE_E', MonkeyDevice.DOWN_AND_UP)
device.press('KEYCODE_S', MonkeyDevice.DOWN_AND_UP)
device.press('KEYCODE_T', MonkeyDevice.DOWN_AND_UP)
device.touch(650,250, MonkeyDevice.DOWN_AND_UP)

# Takes a screenshot
result = device.takeSnapshot()

# Writes the screenshot to a file
result.writeToFile('search.png','png')
