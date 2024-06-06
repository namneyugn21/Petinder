import React, { useState } from 'react';
import { RiMenu3Line, RiCloseLine } from 'react-icons/ri';
import { NavLink } from 'react-router-dom';
import './navbar.css';
import logo from '../../assets/logo.png';

const Menu = () => (
    <>
        <div className='navbar__menu-item'>
            <NavLink to='/'>
                <a href='#about'>About</a>
            </NavLink>
        </div>
        <div className='navbar__menu-item'><a href='#discover'>Discover</a></div>
        <div className='navbar__menu-item'><a href='#learn'>Learn</a></div>  
    </>
);

const Navbar = () => {
    const [toggleMenu, setToggleMenu] = useState(false);

    return (
        <div className='navbar'>
            <NavLink to='/'>
                <div className='navbar__title inter__bold'>
                    <div className='navbar__logo'>
                        <img src={logo} alt='logo' />
                    </div>
                    <div>
                        Petinder
                    </div>                
                </div>
            </NavLink>
            <div className='navbar__menu ibm-plex-mono-regular'>
                <Menu />
                <div className='navbar__menu-item'>
                    <NavLink to='/login'>
                        <button className='navbar__menu-button ibm-plex-mono-regular' type='button'>Sign In</button>
                    </NavLink>
                </div>
            </div>
            {<div className='navbar__menu-toggle'>
                {
                    toggleMenu 
                        ? <RiCloseLine color = '#000' size={25} onClick={() => setToggleMenu(!toggleMenu)} />
                        : <RiMenu3Line color = '#000' size={25} onClick={() => setToggleMenu(!toggleMenu)} />
                }
                { // Adding mobile menu dropdown
                    toggleMenu && (
                        <div className='navbar__menu-dropdown fade-in ibm-plex-mono-regular'>
                            <div className='navbar__menu-item tracking-in-expand'>
                                <NavLink to='/'>
                                    <a href='#about'>About</a>
                                </NavLink>
                            </div>
                            <div className='navbar__menu-item tracking-in-expand'><a href='#discover'>Discover</a></div>
                            <div className='navbar__menu-item tracking-in-expand'><a href='#learn'>About</a></div>  
                            <div className='navbar__menu-item tracking-in-expand'>
                                <NavLink to='/login'>
                                    <button className='navbar__menu-button ibm-plex-mono-regular' type='button'>Sign In</button>
                                </NavLink>
                            </div>  
                        </div> 
                    )
                }
            </div>}
        </div>
    )
}

export default Navbar