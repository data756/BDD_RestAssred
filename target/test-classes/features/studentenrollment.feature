#Author: bhavya.mandaliya21@gmail.com
@StudentEnrollment
Feature: Validating Student Enrollment project CURD operations.
  
  @addStudent
  Scenario Outline: Verify if Student is successfully enrolled 
    Given student information : "<id>","<firstName>","<lastName>","<classInfo>", "<Nationality>"
    When user calls "addstudent" api with "post" http request
  	Then  resonse status should be 201
    And  verify the response content with fetched backend database record
    

Examples:
		|id    | firstName   | lastName        | classInfo   | Nationality     |  
    |87    | Robert      |  Fernandez      |  5-D        | Sri Lanka       |
    
    
 @fetchstudent
 Scenario: Verify fetch student infromation functionality 
    Given you have enrolled student information : 3
    When user calls "fetchStudent" api with "get" http request
  	Then  resonse status should be 200
    And  verify the response content with fetched backend database record
    
    
 @updatestudent
 Scenario: Verify update student infromation functionality 
    Given student information : "<id>","<firstName>","<lastName>","<classInfo>", "<Nationality>"
    When user calls "updatestudent" api with "put" http request
  	Then  resonse status should be 200
    And  verify the response content with fetched backend database record
    
 Examples:
		|id   | firstName | lastName  | classInfo  | Nationality    |  
    |2    | Auliana   | Gomez     |  11-D      | Colombia       |

    
    
  Scenario: Verify the delete student information functionality
  Given you have enrolled student information in payload : 11
  When user calls "deletestudent" api with "delete" http request
  Then resonse status should be 200
  And  verify the response content with fetched backend database record


Scenario: Verify the fetch students information by class information.
  Given you have student class information : "4-C"
  When user calls "fetchStudents" api with "get" http request
  Then resonse status should be 200
  And  verify the response content with fetched backend database record
  


  