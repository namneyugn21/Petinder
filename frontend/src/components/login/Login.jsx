import React, { useEffect } from 'react';
import { FcGoogle } from "react-icons/fc";
import { RiCloseLine, RiFacebookCircleFill } from 'react-icons/ri';
import { MdPets } from 'react-icons/md';
import './login.css';
import logo from '../../assets/logo.png';

// Login button component
const LoginButton = ({ url, icon: Icon, label, disabled, color, onClick }) => {
    return ( // Return a button with an icon and a label
        <a href={disabled ? '#' : url} aria-disabled={disabled} onClick={onClick ? (e) => {e.preventDefault(); onClick(); } : null}>
            <button className={`navbar__menu-sign-in-form__icon-container ${disabled ? 'disabled' : ''}`} disabled={disabled}>
                <div className='navbar__menu-sign-in-form__icon-inner-container'>
                    <Icon color={color} />
                </div>
                <p className='montserrat-medium'>{label}</p>
            </button>
        </a>
    );
};

const Login = ({ isLoginFormVisible, toggleLoginForm, isFadingOut }) => {
    // OAuth URLs
    const googleOauthUrl = process.env.REACT_APP_GOOGLE_OAUTH_URL;
    const facebookOauthUrl = '';  // Empty for now

    // Prevent scrolling when the login form is visible
    useEffect(() => {
        if (isLoginFormVisible) {
            document.body.style.overflow = 'hidden';
        } else {
            document.body.style.overflow = 'auto';
        }
    })

    // State for shelter login
    const [isShelterLogin, setIsShelterLogin] = React.useState(false);

    return (
        <>
            {isLoginFormVisible && (
                <>
                    {/* Overlay */}
                    <div className={`overlay ${isFadingOut ? 'fade-out' : 'fade-in'}`} onClick={toggleLoginForm}></div>
                    { /* Sign In Options */}
                    <div className={`navbar__menu-sign-in-form ${isFadingOut ? 'fade-out' : 'fade-in'}`}>
                        <RiCloseLine id='close-icon' color='#042c54' size={25} onClick={toggleLoginForm} />
                        <img src={logo} alt='logo' draggable='false'/>

                        { isShelterLogin ? (
                            // If the user wants to sign in as a shelter
                            <>
                                <h2 className='montserrat-bold'>Shelter Sign In</h2>
                                <p className='montserrat-regular'>Are you a Petinder user? <span onClick={() => setIsShelterLogin(false)} className='clickable'>Sign In</span></p>
                                <form action='#' method='POST' className='shelter-signin montserrat-medium'>
                                    <input type='text' className='montserrat-regular' name='username' placeholder='Username' required />
                                    <input type='password' className='montserrat-regular' name='password' placeholder='Password' required />
                                    <button type='submit' className='montserrat-medium'>Sign In</button>
                                </form>
                            </>
                        ) : ( 
                            // If the user wants to sign in as a user
                            <>
                                <h2 className='montserrat-bold'>Get Started !</h2>
                                <p className='montserrat-regular'>By signing in, you agree to our terms and conditions.</p>
                            
                                {/* Google OAuth */}
                                <LoginButton
                                    url={googleOauthUrl}
                                    icon={FcGoogle}
                                    label='Google Account'
                                />

                                {/* Shelter Sign In */}
                                <LoginButton
                                    url='#'
                                    icon={MdPets}
                                    label='Shelter'
                                    color={'#042c54'}
                                    onClick={() => setIsShelterLogin(true)}
                                />

                                {/* Facebook OAuth (Disabled) */}
                                {/* <LoginButton
                                    url={facebookOauthUrl}
                                    icon={RiFacebookCircleFill}
                                    label='Facebook'
                                    disabled={true}
                                    color='#042c54'
                                /> */}
                            </>
                        )}
                    </div>
                </>
            )}
        </>
    );
};

export default Login;