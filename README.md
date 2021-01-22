# FtpRx: Simple implementation of a FTP server
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Specification Support

- **[RFC 959](https://tools.ietf.org/html/rfc959) - File Transfer Protocol** *(partially implemented)*
- **[RFC 373](https://tools.ietf.org/html/rfc373) - FTP Extension: XSEN** *(not implemented)*
- **[RFC 2228](https://tools.ietf.org/html/rfc2228) - FTP Security Extensions** *(not implemented)*
- **[RFC 2428](https://tools.ietf.org/html/rfc2428) - FTP Extensions for IPv6 and NATs** *(not implemented)*
- **[RFC 697](https://tools.ietf.org/html/rfc697) - CWD Command of FTP** *(not implemented)*

## Running and Debugging
Running project if very simple, just open up your shell/terminal and enter the following command in root project directory.

```
$ ./gradlew application:run
```

## Contributing
If you want to submit code back to the project, please take a moment to review over the guidelines below.

### Code Style
FtpRx stands by the usual java style, but we don't have an official coding standard.

**Please do not do any of the following:**
* Underscores in identifiers
* Hungarian notation
* Prefixes for fields or arguments
* Curly braces on new lines

**A few additional notes to keep in mind:**
* When creating a new file, make sure to add the Apache file header, as you can see [here](https://opensource.org/licenses/Apache-2.0)
* If you create a new class, please add documentation that explains the usage and scope of the class. You don't have to add javadocs for methods that are self explanatory.

### Size of code changes
To reduce the chances of introducing errant behavior, and to increase the chance that your pull request gets merged, we ask you to keep the requests small and focused.
* Submit a pull request dedicated to solving one issue/feature.
* Keep the code changes as small as possible, its very unlikely that huge change sets of files will be merged


## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/Adiras/FtpRx/issues).

## License

    Copyright 2019, FtpRx Contributors

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
