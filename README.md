
# react-native-serv-pay

## Getting started

`$ npm install react-native-serv-pay --save`

### Mostly automatic installation

`$ react-native link react-native-serv-pay`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-serv-pay` and add `RNServPay.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNServPay.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.serv.reactnative.RNServPayPackage;` to the imports at the top of the file
  - Add `new RNServPayPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-serv-pay'
  	project(':react-native-serv-pay').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-serv-pay/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-serv-pay')
  	```


## Usage
```javascript
import RNServPay from 'react-native-serv-pay';

// TODO: What to do with the module?
RNServPay;
```
  