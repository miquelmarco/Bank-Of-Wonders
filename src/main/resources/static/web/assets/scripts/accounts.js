let { createApp } = Vue;

createApp({
    data() {
        return {
            clientName: '',
            accounts: []
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get(`http://localhost:8080/api/clients/1`)
                .then(res => {
                    this.client = res.data
                    this.clientName = this.client.firstName + ' ' + this.client.lastName
                    this.accounts = this.client.accounts
                }).catch(err => console.error(err))
        },
    }
}).mount("#app")