import React, { useEffect } from 'react'
import Navbar from '../../components/navbar/Navbar'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons'
import './User.css'

const User = () => {
    const [isEditingFirstName, setIsEditingFirstName] = React.useState(false);
    const [isEditingMiddleName, setIsEditingMiddleName] = React.useState(false);
    const [isEditingLastName, setIsEditingLastName] = React.useState(false);
    const [isEditingPhoneNumber, setIsEditingPhoneNumber] = React.useState(false);
    const [isEditingDescription, setIsEditingDescription] = React.useState(false);
    const [isEditing, setIsEditing] = React.useState(false);

    // State for the user's information
    const [user, setUser] = React.useState({
        firstName: '',
        middleName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        description: ''
    });

    // Create a function to handle the edit button
    const handleEdit = (attribute) => {
        setIsEditingFirstName(attribute === 'firstName');
        setIsEditingLastName(attribute === 'lastName');
        setIsEditingMiddleName(attribute === 'middleName');
        setIsEditingDescription(attribute === 'description');
        setIsEditingPhoneNumber(attribute === 'phoneNumber');

        setIsEditing(attribute === 'firstName' || attribute === 'lastName' || attribute === 'middleName' || attribute === 'description' || attribute === 'phoneNumber');
    }

    // Create a function to handle the cancel button 
    const handleCancel = () => {
        setIsEditingFirstName(false);
        setIsEditingLastName(false);
        setIsEditingMiddleName(false);
        setIsEditingDescription(false);
        setIsEditingPhoneNumber(false);
        setIsEditing(false);
    }

    // Create a function to handle the save button
    const handleSave = async () => {
        try {
            const response = await fetch('http://petinder.bao2803.co/user/' + localStorage.getItem('userId'), {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            });
            if (response.status === 200) {
                setIsEditingFirstName(false);
                setIsEditingLastName(false);
                setIsEditingMiddleName(false);
                setIsEditingDescription(false);
                setIsEditingPhoneNumber(false);
                setIsEditing(false);
            }
        } catch (error) {
            console.log('ERROR: ', error);
        }
    }

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await fetch('http://petinder.bao2803.co/user/' + localStorage.getItem('userId'));
                const responseJson = await response.json();
                const data = responseJson.data;
                setUser({
                    firstName: data.firstName || '',
                    middleName: data.middleName || '',
                    lastName: data.lastName || '',
                    email: data.email || '',
                    description: data.description || '',
                    phoneNumber: data.phoneNumber || ''
                });
            } catch (error) {
                console.log('ERROR: ', error);
            }
        }
        fetchUserData();
    }, [])

    return (
        <>
            <Navbar/>
            <div className='user-container'>
                <div className='user-info-container'>
                    <p className='user-info-header montserrat-bold'>Account Information</p>
                    <div className='user-info-row'>
                        <div className='user-info'>
                            <div style={{ display: 'flex', gap: '5px' }}>
                                <p className='user-info-subheader montserrat-semibold'>First Name</p>
                                <FontAwesomeIcon className='user-info-edit-button' icon={faPenToSquare} style={{ color: '042c54' }} onClick={() => handleEdit('firstName')} />
                            </div>
                            { isEditingFirstName ?
                                <input 
                                    type='text' 
                                    className='user-info-input montserrat-medium' 
                                    placeholder='John' 
                                    value={user.firstName}
                                    onChange={(e) => setUser({...user, firstName: e.target.value})}
                                />
                                :
                                <p className='montserrat-medium'>{user.firstName}</p>
                            }
                        </div>
                        <div className='user-info'>
                            <div style={{ display: 'flex', gap: '5px' }}>
                                <p className='user-info-subheader montserrat-semibold'>Middle Name</p>
                                <FontAwesomeIcon className='user-info-edit-button' icon={faPenToSquare} style={{ color: '042c54' }} onClick={() => handleEdit('middleName')} />
                            </div>
                            { isEditingMiddleName ?
                                <input 
                                    type='text' 
                                    className='user-info-input montserrat-medium' 
                                    placeholder='Middle Name' 
                                    value={user.middleName}
                                    onChange={(e) => setUser({...user, middleName: e.target.value})}
                                />
                                :
                                <p className='montserrat-medium'>{user.middleName}</p>
                            }
                        </div>
                    </div>
                    <div className='user-info-row'>
                        <div className='user-info'>
                            <div style={{ display: 'flex', gap: '5px' }}>
                                <p className='user-info-subheader montserrat-semibold'>Last Name</p>
                                <FontAwesomeIcon className='user-info-edit-button' icon={faPenToSquare} style={{ color: '042c54' }} onClick={() => handleEdit('lastName')} />
                            </div>
                            { isEditingLastName ?
                                <input 
                                    type='text' 
                                    className='user-info-input montserrat-medium' 
                                    placeholder='Doe' 
                                    value={user.lastName}
                                    onChange={(e) => setUser({...user, lastName: e.target.value})}
                                />
                                :
                                <p className='montserrat-medium'>{user.lastName}</p>
                            }                        
                        </div>
                        <div className='user-info'>
                            <div style={{ display: 'flex', gap: '5px' }}>
                                <p className='user-info-subheader montserrat-semibold'>Phone Number</p>
                                <FontAwesomeIcon className='user-info-edit-button' icon={faPenToSquare} style={{ color: '042c54' }} onClick={() => handleEdit('phoneNumber')} />
                            </div>
                            { isEditingPhoneNumber ?
                                <input 
                                    type='text' 
                                    className='user-info-input montserrat-medium' 
                                    placeholder='123-456-7890' 
                                    value={user.phoneNumber}
                                    onChange={(e) => setUser({...user, phoneNumber: e.target.value})}
                                />
                                :
                                <p className='montserrat-medium'>{user.phoneNumber}</p>
                            }                        
                        </div>
                    </div>
                    <div className='user-info-row'>
                        <div className='user-info'>
                            <p className='user-info-subheader montserrat-semibold'>Email</p>
                            <p className='montserrat-medium'>{user.email}</p>
                        </div>
                    </div>
                    <div className='user-info-row' style={{ borderBottom: 'none' }}>
                        <div className='user-info'>
                            <div style={{ display: 'flex', gap: '5px' }}>
                                <p className='user-info-subheader montserrat-semibold'>Description</p>
                                <FontAwesomeIcon className='user-info-edit-button' icon={faPenToSquare} style={{ color: '042c54' }} onClick={() => handleEdit('description')}/>
                            </div>
                            { isEditingDescription ?
                                <textarea 
                                    type='text' 
                                    className='user-info-input montserrat-medium' 
                                    placeholder='Tell us about yourself...' 
                                    style={{ width: '100%', minHeight: '100px', resize: 'vertical', lineHeight: '1.5' }}
                                    value={user.description}
                                    onChange={(e) => setUser({...user, description: e.target.value})}
                                />
                                :
                                <p className='montserrat-medium' style={{ lineHeight: '1.5' }}>{user.description}</p>
                            }                   
                        </div>
                    </div>
                    <div className='user-info-utilities'>
                        <button 
                            className='user-info-utilities-button montserrat-semibold' 
                            disabled={!isEditing}
                            onClick={handleSave}
                        >
                                Save
                        </button>
                        <button 
                            className='user-info-utilities-button montserrat-semibold' 
                            disabled={!isEditing} 
                            onClick={handleCancel}
                        >
                            Cancel
                        </button>
                    </div>
                </div>
            </div>
        </>

    )
}

export default User