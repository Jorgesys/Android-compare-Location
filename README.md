# Android-compare-Location
Compare the approximate distance in meters between device location and given locations.

# What does it look like?
![Jorgesys Android_compare location](https://i.stack.imgur.com/YXdHL.png)

# Important notes:
- Change the getListOfPostitions() method to get your own list of locations to be compared with the device location.
- inside the comparePrintInfo() delete the block of code that is overriding the device location.
        /*TODO: Jorgesys, * Delete this block that overrides the device location. Used for testing purposes */
        deviceLocation.setLatitude(47.160177);
        deviceLocation.setLongitude(27.585999);
        /*-----------------------------------------*/

