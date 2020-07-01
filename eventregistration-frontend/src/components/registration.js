
import axios from 'axios'
var config = require('../../config')

// var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create ({
    baseURL: backendUrl,
    // headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

  export default {
      name: 'eventregistration',
      data() {
          return {
              persons: [],
              organizers: [],
              newPerson: '',
              personType: 'Person',
              errorPerson: '',
              response: [],
          }
      },
      created: function() {
          AXIOS.get('/persons')
          .then(response => {
              this.persons = response.data
              console.log(this.persons)
          })
          .catch(e => {
              this.errorPerson = e
          });
          AXIOS.get('/organizers')
          .then(response => {
              this.organizers = response.data
          })
          .catch(e => {
              this.errorPerson = e
          });
      },
      methods: {
        getPersons: function() {
            AXIOS.get('/persons')
            .then(response => {
                this.persons = response.data
            })
            .catch(e => {
                this.errorPerson = e
            });
        },
        createPerson: function(personType, personName) {
            if(personType == "Person") {
                AXIOS.post('/persons/'.concat(personName), {}, {})
                .then(response => {
                    this.persons.push(response.data)
                    this.errorPerson = ''
                    this.newPerson = ''
                })
                .catch(e => {
                    e = e.response.data.message ? e.response.data.message : e
                    this.newPerson = ''
                    this.errorPerson = e
                    console.log(e);
                });
            }
            if(personType == "Organizer") {
                AXIOS.post('/organizers/'.concat(personName), {}, {})
                .then(response => {
                    this.persons.push(response.data)
                    this.organizers.push(response.data)
                    this.errorPerson = ''
                    this.newPerson = ''
                })
                .catch(e => {
                    e = e.response.data.message ? e.response.data.message : e
                    this.newPerson = ''
                    this.errorPerson = e
                    console.log(e);
                });
            }
        },
        deletePerson: function(personName) {
            AXIOS.delete('/persons/'.concat(personName))
            .then(
                this.getPersons
            )
            .catch(e => {
                var errorMessage = e.message
                console.log(errorMessage)
                this.errorPerson = errorMessage
            })
        }
      }
  }