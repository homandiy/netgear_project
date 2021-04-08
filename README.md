# Project : NetGear Mobile Developer Excise 2021
Editor: Homan Huang
Location: San Francisco, CA, USA

## Api Information
Display Manifest and Images data from https://afternoon-bayou-28316.herokuapp.com/

Authentication: Access API by Api Key set in gradle.properties
Security: gradle.properties must be inserted in the .gitignore


## Project Folder Structure

app : application

data : data section of MVVM
data/local/entity: Entity of Room database
data/local/dao: DAO functions of Room database
data/local: room database

data/remote/service: RestApi service
data/remote/pojo: POJO classes for RestApi

di: module for Dagger Hilt

helper: constants and shared functions

ui/main: MainActivity
ui/main/adapter: listener and adapter functions



