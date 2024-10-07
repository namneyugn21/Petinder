import React from 'react';
import { FcGoogle } from "react-icons/fc";
import { RiCloseLine, RiFacebookCircleFill } from 'react-icons/ri';
import './login.css';
import logo from '../../assets/logo.png';

// Login button component
const LoginButton = ({ url, icon: Icon, label, disabled, color }) => {
    return ( // Return a button with an icon and a label
        <a href={disabled ? '#' : url} aria-disabled={disabled}>
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
    const googleOauthUrl = process.env.REACT_APP_GOOGLE_OAUTH_URL;
    const facebookOauthUrl = '';  // Empty for now

    return (
        <>
            {isLoginFormVisible && (
                <>
                    <div className={`overlay ${isFadingOut ? 'fade-out' : 'fade-in'}`} onClick={toggleLoginForm}></div>
                    <div className={`navbar__menu-sign-in-form ${isFadingOut ? 'fade-out' : 'fade-in'}`}>
                        <RiCloseLine id='close-icon' color='#042c54' size={25} onClick={toggleLoginForm} />
                        <img src={logo} alt='logo' />
                        <h2 className='montserrat-bold'>Get Started !</h2>
                        <p className='montserrat-regular'>By signing in, you agree to our terms and conditions.</p>
                        
                        {/* Google OAuth */}
                        <LoginButton
                            url={googleOauthUrl}
                            icon={FcGoogle}
                            label='Google Account'
                        />

                        {/* Facebook OAuth (Disabled) */}
                        <LoginButton
                            url={facebookOauthUrl}
                            icon={RiFacebookCircleFill}
                            label='Facebook'
                            disabled={true}
                            color='#042c54'
                        />
                    </div>
                </>
            )}
        </>
    );
};

export default Login;
