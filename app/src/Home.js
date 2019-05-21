import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';
import { withCookies } from 'react-cookie';

class Home extends Component {

    state = {
        isLoading: true,
        isAuthenticated: false,
        user: undefined
    }

    constructor(props) {
        super(props);
        const {cookies} = props;
        this.state.csrfToken = cookies.get('XSRF-TOKEN');
        this.login = this.login.bind(this);
        this.logout = this.logout.bind(this);
    }

    async componentDidMount() {

        document.title = 'Store';

        const user = await (await fetch("/api/user", {
            method: 'GET',
            headers: {
                'X-XSRF-TOKEN': this.state.csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            credentials: 'include'})
        ).json();

        if (user.name == null) {
            this.setState(({isAuthenticated: false}));
        } else {
            this.setState({isAuthenticated: true, user: user.name});
        }
    }

    async login(user) {

        await fetch('/login', {

            method: 'POST',
            headers: {
                'X-XSRF-TOKEN': this.state.csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: "username=" + user + "&password=" + user,
            credentials: 'include'
        });
        this.props.history.push('/');

        let port = (window.location.port ? ':' + window.location.port : '');
        if (port === ':3000') {
            port = ':8080';
        }
        window.location.href = '//' + window.location.hostname + port;

    }

    logout() {
        fetch('/logout', {
            method: 'GET',
            headers: {'X-XSRF-TOKEN': this.state.csrfToken},
            credentials: 'include'
            }).then(res => res.json())
        .then(response => {

            let port = (window.location.port ? ':' + window.location.port : '');
            if (port === ':3000') {
                port = ':8080';
            }
            window.location.href = '//' + window.location.hostname + port;
        });
    }

    render() {

        const message = this.state.user ?
            <h2>Welcome, {this.state.user}!</h2> :
            <p>Please log in to manage your cart!</p>;

        const chris = "chris";

        const button = this.state.isAuthenticated ?
            <div>
                <Button color="link"><Link to="/items">View Store</Link></Button>
                <br/>
                <br/>
                <Button color="danger" onClick={this.logout}>Logout</Button>
            </div> :
            <div>
                <Button color="success" onClick={() => this.login(chris)}>Login As Chris</Button>
            </div>
            ;

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    {message}
                    {button}
                </Container>
            </div>
        );
    }
}

export default withCookies(Home);