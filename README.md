# Project : NetGear Mobile Developer Excise 2021
    Editor: Homan Huang
    Location: San Francisco, CA, USA


# Api Information
    Display Manifest and Images data from 
    https://afternoon-bayou-28316.herokuapp.com/

* Authentication: Access API by Api Key set in gradle.properties
* Security: gradle.properties must be inserted in the .gitignore 


# Project Folder Structure

* app : application

* data : data section of MVVM
    data/local/dao: DAO functions of Room database
    data/local/entity: Entity of Room database
    data/local/storage: sharedPreference storage
    data/local: local storage
    
    data/remote/pojo: POJO classes for RestApi
    data/remote/service: RestApi service

* di : modules for Dagger Hilt

* helper : constants and shared functions

* repository : repository files

* ui : MainActivity, fragments and viewModels
    ui/adapter: listener and adapter functions


# Test Cases: All in AndroidTest folder

* data/local: 3 tests
    ImageChaceTest
    ImageDaoTest
    ManifestDaoTest

* data/remote: 2 tests
    ApiServiceTest
    HttpTest

* ui/error: 4 tests
    ImageDownloadErrorTest
    ManifestDownloadErrorTest
    ManifestLoadDatabaseErrorTest
    ZeroDataErrorTest
  
* ui: 3 espresso tests
    FirstGroupTest
    SecondGroupTest
    ThridGroupTest