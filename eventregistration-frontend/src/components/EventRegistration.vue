<template>
    <b-container>
        <h1> EVENT REGISTRATION</h1>
        <b-row>
            <b-col>
                <h3> Registrations </h3>
                <div>
                    <input type="text" v-model="newPerson" placeholder="Person Name">
                    <select v-model="personType">
                        <option value="Person"> Person </option>
                        <option value="Organizer"> Organizer </option>
                    </select>
                    <b-button variant="success" v-bind:disabled="!newPerson" @click="createPerson(personType, newPerson)"> Create </b-button>
                </div>
                <p>
                    <span v-if="errorPerson" style="color:red"> Error: {{errorPerson}} </span>
                </p>
                <div id="table-heading">
                    <h5> Name </h5>
                    <h5> Events </h5>
                    <h5> Credit Card </h5>
                    <h5> Amount </h5>
                    <h5>  </h5>
                </div>
                <b-row id="table-data" v-for="(person, index) in persons" v-bind:key="person.index" :key="index">
                    <p> {{ person.name }}</p>
                    <p> Event to Attend </p>
                    <p> xxxx-xxxx-xxxx-xxxx</p>
                    <p> $xxx.xx</p>
                    <p>
                    <b-button variant="danger" size="sm" @click="deletePerson(person.name)"> X </b-button>
                    </p>
                </b-row> 
            </b-col>
            <b-col>
                <h3> Events </h3>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <h3> Assign Organizers </h3>
            </b-col>
            <b-col>
                <h3> Registrations </h3>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <h3> Payment </h3>
            </b-col>
        </b-row>
    </b-container>
    
</template>
<script src="./registration.js">

</script>
<style>
    #table-data:hover {
        background-color: #ddd;
    }
    #table-heading {
        display: flex;
        justify-content: space-around;
        vertical-align: bottom;
        background-color: #C0E0DE;
        margin-top: 10px;
    }
    #table-data {
        display: flex;
        justify-content: space-around;
    }

</style>
