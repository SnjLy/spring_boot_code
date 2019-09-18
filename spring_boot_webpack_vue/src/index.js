import Vue from 'vue'
import App from './App.vue'

import './assert/style/test.css'

const root = document.createElement("div");
document.body.appendChild(root);


new Vue({
    render: h => h(App),
}).$mount(root);
