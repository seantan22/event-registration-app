
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
              newPerson: '',
              errorPerson: '',
              response: []
          }
      },
      created: function() {
          AXIOS.get('/persons')
          .then(response => {
              this.persons = response.data
          })
          .catch(e => {
              this.errorPerson = e
          });
      },
      methods: {
          createPerson: function(personName) {
              AXIOS.post('/persons/'.concat(personName), {}, {})
              .then(response => {
                  this.persons.push(response.data)
                  this.newPerson = ''
                  this.errorPerson = ''
              })
              .catch(e => {
                  var errorMessage = e.message
                  console.log(errorMsg)
                  this.errorPerson = errorMessage
              });
          }
      }
  }