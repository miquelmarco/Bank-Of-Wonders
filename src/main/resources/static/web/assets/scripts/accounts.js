let { createApp } = Vue;

createApp({
    data() {
        return {
            clientName: '',
            accountOne: {},
            accountTwo: {},
            selectedAccount: ''
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
                    this.accountOne = res.data.accounts[0]
                    this.accountTwo = res.data.accounts[1]
                }).catch(err => console.error(err))
        },
    }
}).mount("#app")