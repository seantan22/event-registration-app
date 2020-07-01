<template>
    <b-container>
        <h1 class="pt-4"> Event Registration </h1>
        <b-row class="mt-3">
            <b-col id="section" class="mx-2 p-4">
                <h3> Registrations </h3>
                <div>
                    <input type="text" v-model="newPerson" placeholder="Person Name">
                    <select v-model="personType">
                        <option value="Person"> Person </option>
                        <option value="Organizer"> Organizer </option>
                    </select>
                    <b-button variant="success" size="sm" v-bind:disabled="!newPerson" @click="createPerson(personType, newPerson)"> Create </b-button>
                </div>
                <p>
                    <span v-if="errorPerson" style="color:red"> Error: {{errorPerson}} </span>
                </p>
                <div id="table-heading" class="pt-2">
                    <h5> Name </h5>
                    <h5> Events </h5>
                    <h5> Credit Card </h5>
                    <h5> Amount </h5>
                    <h5>  </h5>
                </div>
                <b-row id="table-data" v-for="(person, index) in persons" v-bind:key="person.index" :key="index">
                    <p> {{ person.name }}</p>
                    <p v-for="(event, index) in person.eventsAttended" v-bind:key="person.index">
                        <span> {{event.name}} </span>
                    </p>
                    <p> xxxx-xxxx-xxxx-xxxx</p>
                    <p> $xxx.xx</p>
                    <p>
                        <b-button variant="danger" size="sm" @click="deletePerson(person.name)"> X </b-button>
                    </p>
                </b-row> 
            </b-col>
            <b-col id="section" class="mx-2 p-4">
                <h3> Events </h3>
                <div>
                    <input type="text" v-model="newEvent.name" placeholder="Event Name">
                    <input type="text" v-model="newEvent.description" placeholder="Description">
                    <input type="date" v-model="newEvent.date">
                    <input type="time" v-model="newEvent.startTime"> 
                    <input type="time" v-model="newEvent.endTime">
                    <b-button variant="success" size="sm" v-bind:disabled="!newEvent.name" @click="createEvent(newEvent)"> Create </b-button>
                </div>
                <div id="table-heading" class="pt-2">
                    <h5> Name </h5>
                    <h5> Description </h5>
                    <h5> Date </h5>
                    <h5> Start Time </h5>
                    <h5> End Time </h5>
                    <h5> </h5>
                </div>
                <b-row id="table-data" v-for="(event, index) in events" v-bind:key="event.index" :key="index">
                    <p> {{ event.name }} </p>
                    <p> {{ event.description }} </p>
                    <p> {{ event.date }} </p>
                    <p> {{ event.startTime }} </p>
                    <p> {{ event.endTime }} </p>
                    <p>
                        <b-button variant="danger" size="sm" @click="deleteEvent(event.name)"> X </b-button>
                    </p>
                </b-row> 
            </b-col>
        </b-row>
        <b-row class="mt-3">
            <b-col id="section" class="mx-2 p-4">
                <h3> Assign Organizers </h3>
            </b-col>
            <b-col id="section" class="mx-2 p-4">
                <h3> Register </h3>
                <label>Person:
                    <select v-model="selectedPersonR">
                        <option disabled value="">Please select one...</option>
                        <option v-for="(person, index) in persons" v-bind:key="person.index" :key="index">
                            {{person.name}}
                        </option>
                    </select>
                </label>
                <label>Event:
                    <select v-model="selectedEventR">
                        <option disabled value="">Please select one...</option>
                        <option v-for="(event, index) in events" v-bind:key="event.index" :key="index">
                            {{event.name}}
                        </option>
                    </select>
                </label>
                <div>
                    <b-button variant="success" size="sm" v-bind:disabled="!selectedPersonR || !selectedEventR" @click="registerEvent(selectedPersonR, selectedEventR)">
                        Register
                    </b-button>
                </div>
                <span v-if="errorRegistration" style="color:red">Error: {{errorRegistration}}</span>
            </b-col>
            <b-col id="section" class="mx-2 p-4">
                <h3> Record Payment </h3>
            </b-col>
        </b-row>
    </b-container>
    
</template>
<script src="./registration.js">

</script>
<style>
    #section {
        border: 3px solid;
        border-radius: 20px;
    }
    #table-data:hover {
        background-color: #ddd;
    }
    #table-heading {
        display: flex;
        justify-content: space-around;
        background-color: #C0E0DE;
    }
    #table-data {
        display: flex;
        justify-content: space-around;
    }

</style>
