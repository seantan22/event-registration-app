import Vue from 'vue'
import Router from 'vue-router'

import EventRegistration from '@/components/EventRegistration'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'EventRegistration',
      component: EventRegistration
    }
  ]
})