import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { instanceOf } from 'prop-types';
import { Cookies, withCookies } from 'react-cookie';


class ItemAdd extends Component {

    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    emptyCartItem = {
        quantity: 0
    };

    emptyItem = {
        name: '',
        description: '',
        price: 0,
        carts: []
    };

    constructor(props) {
        super(props);
        const {cookies} = props;
        this.state = {
            cart: this.emptyCartItem,
            item: this.emptyItem,
            csrfToken: cookies.get('XSRF-TOKEN')
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {

        document.title = 'Purchase Item';
        var id = this.props.match.params.id;
        const item = await (await fetch("/api/item/" + id, {credentials: 'include'})).json();
        this.setState({
            item: item
        });
        this.setState({
            cart: this.state.item.carts.length > 0 ? this.state.item.carts[0] : this.emptyCartItem
        });
    }

    handleChange(event) {
        const target = event.target;
        const value = parseInt(target.value);
        let cart = {...this.state.cart};
        cart.quantity = value;
        cart.item = this.state.item;
        this.setState({cart});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {cart} = this.state;

        await fetch('/api/cart', {
            method: (this.state.item.carts.length > 0) ? 'PUT' : 'POST',
            headers: {
                'X-XSRF-TOKEN': this.state.csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(cart),
            credentials: 'include'
        });
        this.props.history.push('/items');
    }

    render() {
        const {cart} = this.state
        const {item} = this.state
        const title = <h2>{cart ? 'Add ' + item.name : 'Update'}</h2>

        return <div>
            <AppNavbar />
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Select a quantity</Label>
                        <Input type="number" min="0" name="cart" id="cart" value={cart.quantity}
                               onChange={this.handleChange} autoComplete="cart"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/items">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>

    }
}

export default withCookies(withRouter(ItemAdd));