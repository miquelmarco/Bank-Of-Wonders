let { createApp } = Vue;

createApp({
    data() {
        return {
            allAccounts:[],
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get(`http://localhost:8080/api/accounts/{id}`)
                .then(res => {
                    this.allAccounts = res.data
                    console.log(this.allAccounts)
                }).catch(err => console.error(err))
        },
    }
}).mount("#app")