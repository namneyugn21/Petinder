import React from 'react';
import { FcGoogle } from "react-icons/fc";
import { RiCloseLine, RiFacebookCircleFill } from 'react-icons/ri';
import './login.css';
import logo from '../../assets/logo.png';

const Login = ({ isLoginFormVisible, toggleLoginForm, isFadingOut }) => {
    return (
        <>
            {isLoginFormVisible && (
                <>
                    <div className={`overlay ${isFadingOut ? 'fade-out' : 'fade-in'}`} onClick={toggleLoginForm}></div>
                    <div className={`navbar__menu-sign-in-form ${isFadingOut ? 'fade-out' : 'fade-in'}`}>
                        <RiCloseLine id='close-icon' color = '#042c54' size={25} onClick={toggleLoginForm} />
                        <img src={logo} alt='logo' />
                        <h2 className='montserrat-bold'>Get Started !</h2>
                        <p className='montserrat-regular'>By signing in, you agree to our terms and conditions.</p>
                        <a href='http://petinder.bao2803.co/auth/oauth2/authorization/google?redirect_uri=http://localhost:3000/success'><button className='navbar__menu-sign-in-form__icon-container'>
                            <div className='navbar__menu-sign-in-form__icon-inner-container'>
                                <FcGoogle />
                            </div>
                            <p className='montserrat-medium'>Google Account</p>
                        </button></a>
                        <div className='navbar__menu-sign-in-form__icon-container '>
                            <div className='navbar__menu-sign-in-form__icon-inner-container'>
                                <RiFacebookCircleFill color = '#042c54' />
                            </div>
                            <p className='montserrat-medium'>Facebook</p>
                        </div>
                    </div>
                </>
            )}
        </>
    );
};

export default Login;
