// Link.react.test.js
import React from 'react';
import AppNavbar from 'AppNavbar';
import renderer from 'react-test-renderer';

it('renders correctly', () => {
    const tree = renderer
        .create(AppNavbar)
        .toJSON();
    expect(tree).toMatchSnapshot();
});