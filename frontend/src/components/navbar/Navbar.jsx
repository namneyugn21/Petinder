import React, { useState, useEffect, useRef } from 'react';
import { RiMenu3Line, RiCloseLine } from 'react-icons/ri';
import { PiPawPrintFill } from "react-icons/pi";
import { NavLink, useNavigate } from 'react-router-dom';
import './navbar.css';
import logo from '../../assets/logo.png';
import Login from '../login/Login';

const Navbar = () => {
    const [isActive, setIsActive] = useState(false);
    const [isToggleMobileMenuVisible, setIsToggleMobileMenuVisible] = useState(false);
    const [isToggleDesktopMenuVisible, setisToggleDesktopMenuVisible] = useState(false);
    const [isLoginFormVisible, setIsLoginFormVisible] = useState(false);
    const [isFadingOut, setIsFadingOut] = useState(false);
    const [isToggleButtonVisible, setIsToggleButtonVisible] = useState(false);
    const [isSignedIn, setIsSignedIn] = useState(false);
    const navigate = useNavigate();
    const dropdownRef = useRef(null);

    // Check if user is signed in by checking if token is stored in local storage
    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            setIsSignedIn(true);
        }
    }, []);

    const signOut = () => {
        localStorage.clear();
        setIsSignedIn(false);
        navigate('/');
        window.location.reload();
    };

    const toggleLoginForm = () => {
        if (isLoginFormVisible) {
            setIsFadingOut(true);
            setIsToggleButtonVisible(false);
            setTimeout(() => {
                setIsLoginFormVisible(false);
                setIsFadingOut(false);
            }, 250);
        } else {
            setIsLoginFormVisible(true);
        }
    };

    const toggleDesktopMenu = () => {
        if (isToggleDesktopMenuVisible) {
            setisToggleDesktopMenuVisible(false);
            setIsActive(false);
        } else {
            setisToggleDesktopMenuVisible(true);
            setIsActive(true);
        }
    };
    // Handle clicks outside the desktop dropdown
    const handleClickOutside = (event) => {
        if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
            setisToggleDesktopMenuVisible(false);
            setIsActive(false);
        }
    };
    // Add event listener on component mount and remove it on unmount
    useEffect(() => {
        // Attach event listener for clicks outside the dropdown
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            // Cleanup event listener on component unmount
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);

    const toggleMobileMenu = () => {
        if (isToggleMobileMenuVisible) {
            setIsFadingOut(true);
            setIsToggleButtonVisible(false);
            setTimeout(() => {
                setIsToggleMobileMenuVisible(false);
                setIsFadingOut(false);
            }, 250);
        } else {
            setIsToggleMobileMenuVisible(true);
            setIsToggleButtonVisible(true);
        }
    };
    
    return (
        <div className='navbar'>
            <NavLink to='/'>
                <div className='navbar__title montserrat-bold'>
                    <div className='navbar__logo'>
                        <img src={logo} alt='logo' />
                    </div>
                    <div>
                        Petinder
                    </div>                
                </div>
            </NavLink>
            <div className='navbar__menu montserrat-medium'>
                {isSignedIn ? (
                    <>
                        <div className={`navbar__menu-signed-in-item ${isActive ? 'navbar__menu-signed-in-item-active' : ''}`} onClick={toggleDesktopMenu}> 
                            <img src={localStorage.getItem('picture')} alt='avatar' draggable='false' />
                            <RiMenu3Line size={25} className='fade-in' />
                        </div>
                    </>
                ) : (
                    <>
                        <div className='navbar__menu-item' onClick={() => navigate('/')}>
                            <a href='#about'>About</a>
                            <PiPawPrintFill size={15} className='fade-in' />
                        </div>
                        <div className='navbar__menu-item' onClick={() => navigate('/')}>
                            <a href='#safety'>Safety</a>
                            <PiPawPrintFill size={15} className='fade-in' />
                        </div>  
                        <div className='navbar__menu-item'>
                            <button className='navbar__menu-button montserrat-medium' type='button' onClick={toggleLoginForm}>
                            Sign In
                            </button>
                        </div>
                    </>
                )
                }
            </div>

            {/* Adding sign in form */}
            <Login isLoginFormVisible={isLoginFormVisible} toggleLoginForm={toggleLoginForm} isFadingOut={isFadingOut} />

            {/* Adding desktop menu dropdown */}
            {isToggleDesktopMenuVisible && (
                <div className='montserrat-regular navbar__menu-desktop-toggle' ref={dropdownRef}>
                    <ul className='navbar__menu-desktop-dropdown'>
                        <li className='navbar__menu-desktop-item' onClick={() => navigate('/')}>
                            About
                        </li>
                        <li className='navbar__menu-desktop-item' onClick={() => navigate('/user')}>
                            Profile
                        </li>
                        <li className='navbar__menu-desktop-item' onClick={() => navigate('/swipe')}>
                            Swipe
                        </li>
                        <div className='navbar__menu-desktop-divider'></div>
                        <a href='#safety'><li className='navbar__menu-desktop-item' onClick={() => navigate('/')}>
                            Safety
                        </li></a>
                        <div className='navbar__menu-desktop-divider'></div>
                        <li className='navbar__menu-desktop-item' onClick={() => {signOut(); setisToggleDesktopMenuVisible(false);}}>
                            Sign Out
                        </li>
                    </ul>
                </div>
            )}

            {/* Adding mobile menu dropdown */}
            {<div className='navbar__menu-mobile-toggle'>
                {
                    isToggleMobileMenuVisible 
                        ? <RiCloseLine className={isToggleButtonVisible ? 'fade-in-button' : 'fade-out-button'} color = '#000' size={25} onClick={() => toggleMobileMenu()} />
                        : <RiMenu3Line className={isToggleButtonVisible ? 'fade-out-button' : 'fade-in-button'} color = '#000' size={25} onClick={() => toggleMobileMenu()} />
                }
                { // Adding mobile menu dropdown
                    isToggleMobileMenuVisible && (
                    <div className={`navbar__menu-mobile-dropdown ${isFadingOut ? 'fade-out' : 'fade-in'} montserrat-regular`}>
                        {isSignedIn ? (
                            <>
                                <div className='navbar__menu-mobile-item tracking-in-expand' onClick={() => navigate('/')}>About</div>
                                <div className='navbar__menu-mobile-item tracking-in-expand' onClick={() => navigate('/user')}>
                                    Profile
                                </div>
                                <div className='navbar__menu-mobile-item tracking-in-expand' onClick={() => navigate('/swipe')}>
                                    Swipe
                                </div>
                                <div className='navbar__menu-mobile-item tracking-in-expand' onClick={() => navigate('/')}><a href='#safety'>Safety</a></div>
                                <div className='navbar__menu-mobile-item tracking-in-expand'>
                                    <button className='navbar__menu-button montserrat-regular tracking-in-expand' type='button' onClick={() => {signOut(); setIsToggleMobileMenuVisible(false);}} style={{ width: '115px' }}>Sign Out</ button>
                                </div>  
                            </>
                        ) : (
                            <>
                            <div className='navbar__menu-mobile-item tracking-in-expand' onClick={() => navigate('/')}>About</div>
                            <div className='navbar__menu-mobile-item tracking-in-expand' onClick={() => navigate('/')}><a href='#safety'>Safety</a></div>
                            <div>
                                <button className='navbar__menu-button montserrat-regular tracking-in-expand' type='button' onClick={() => {toggleLoginForm(); setIsToggleMobileMenuVisible(false);}}>Sign In</button>
                            </div>
                            </>
                        )}
                    </div>
                    )
                }
            </div>}

            {isToggleMobileMenuVisible && <div className="navbar__overlay fade-in" onClick={() => toggleMobileMenu()}></div>}
        </div>
    )
}

export default Navbar;