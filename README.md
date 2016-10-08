# **Landroval-Toast**
Rewrite of our 2016 robot's code using the [Toast API](https://github.com/Open-RIO/ToastAPI)
![Safety Status](https://img.shields.io/badge/Safety-Always-blue.svg)

## Coverage
| Branch        | Coverage        | 
|:-------------:|:-------------:|
| master        | [![codecov](https://codecov.io/gh/RoboEagles4828/LandrovalToast/branch/master/graph/badge.svg)](https://codecov.io/gh/RoboEagles4828/LandrovalToast) |
| collab        | [![codecov](https://codecov.io/gh/RoboEagles4828/LandrovalToast/branch/collab/graph/badge.svg)](https://codecov.io/gh/RoboEagles4828/LandrovalToast) |
| jackie        | [![codecov](https://codecov.io/gh/RoboEagles4828/LandrovalToast/branch/jackie/graph/badge.svg)](https://codecov.io/gh/RoboEagles4828/LandrovalToast) |
| joseph        | [![codecov](https://codecov.io/gh/RoboEagles4828/LandrovalToast/branch/joseph/graph/badge.svg)](https://codecov.io/gh/RoboEagles4828/LandrovalToast) |
| vivek         | [![codecov](https://codecov.io/gh/RoboEagles4828/LandrovalToast/branch/vivek/graph/badge.svg)](https://codecov.io/gh/RoboEagles4828/LandrovalToast) | 

## Builds
| Branch        | Travis        | Appveyor  | 
|:-------------:|:-------------:|:---------:|
| master        | [![Travis Status](https://travis-ci.org/RoboEagles4828/LandrovalToast.svg?branch=master)](https://travis-ci.org/RoboEagles4828/LandrovalToast) | [![Appveyor status](https://ci.appveyor.com/api/projects/status/ibelf4tjpksfugdm/branch/master?svg=true)](https://ci.appveyor.com/project/CheezBallzPi/landrovaltoast/branch/master) |
| collab        | [![Travis Status](https://travis-ci.org/RoboEagles4828/LandrovalToast.svg?branch=collab)](https://travis-ci.org/RoboEagles4828/LandrovalToast) |[![Appveyor status](https://ci.appveyor.com/api/projects/status/ibelf4tjpksfugdm/branch/collab?svg=true)](https://ci.appveyor.com/project/CheezBallzPi/landrovaltoast/branch/collab) |
| jackie        | [![Travis Status](https://travis-ci.org/RoboEagles4828/LandrovalToast.svg?branch=jackie)](https://travis-ci.org/RoboEagles4828/LandrovalToast) |[![Appveyor status](https://ci.appveyor.com/api/projects/status/ibelf4tjpksfugdm/branch/jackie?svg=true)](https://ci.appveyor.com/project/CheezBallzPi/landrovaltoast/branch/jackie) |
| joseph        | [![Travis Status](https://travis-ci.org/RoboEagles4828/LandrovalToast.svg?branch=joseph)](https://travis-ci.org/RoboEagles4828/LandrovalToast) |[![Appveyor status](https://ci.appveyor.com/api/projects/status/ibelf4tjpksfugdm/branch/joseph?svg=true)](https://ci.appveyor.com/project/CheezBallzPi/landrovaltoast/branch/joseph) |
| vivek         | [![Travis Status](https://travis-ci.org/RoboEagles4828/LandrovalToast.svg?branch=vivek)](https://travis-ci.org/RoboEagles4828/LandrovalToast) | [![Appveyor status](https://ci.appveyor.com/api/projects/status/ibelf4tjpksfugdm/branch/vivek?svg=true)](https://ci.appveyor.com/project/CheezBallzPi/landrovaltoast/branch/vivek) |         

## Contribute
To set up this module in your development environment, follow these steps:

1. Clone the repository  
2. Run `gradlew eclipse` for Eclipse, or `gradlew idea` for IntelliJ (Linux/Mac users should use `./gradlew` instead of `gradlew`.)  
3. Edit the `build.gradle` file to reflect your desired configuration (e.g. changing the team number)  

To build this module, simply run `gradlew build`.
To deploy this module to your Robot, simply run `gradlew deploy`.
If you haven't already, you can deploy [Toast](https://github.com/Open-RIO/ToastAPI) to your Robot by running `gradlew toastDeploy`.
