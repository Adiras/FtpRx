# FtpRx: Simple implementation of a FTP server
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f12e9dad759243c182529ea105a4004a)](https://app.codacy.com/gh/Adiras/FtpRx?utm_source=github.com&utm_medium=referral&utm_content=Adiras/FtpRx&utm_campaign=Badge_Grade)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

![FtpRx screenshot](/docs/screenshot.png)

## Specification Support
- **[RFC 959](https://tools.ietf.org/html/rfc959) - File Transfer Protocol** *(partially implemented)*

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

## Found a bug? Missing a specific feature?
For bugs, questions and discussions please use the Github Issues. If you already found a solution to your problem, we would love to review your pull request! Have a look at our **Contributing** section to find out about our coding standards.

## License
FtpRx is licensed under the [Apache License 2.0](LICENSE).
