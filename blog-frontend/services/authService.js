import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth'; // Update with your Spring Boot backend URL

// Register new user
const register = async (userData) => {
  try {
    const response = await axios.post(`${API_URL}/register`, userData);
    return response.data;
  } catch (error) {
    // Handle specific error messages from backend
    if (error.response) {
      throw new Error(
        error.response.data.message || 'Registration failed. Please try again.'
      );
    } else {
      throw new Error('Network error. Please try again later.');
    }
  }
};

// Login user
const login = async (credentials) => {
  try {
    const response = await axios.post(`${API_URL}/login`, credentials);
    
    if (response.data.token) {
      // Store token in localStorage
      localStorage.setItem('token', response.data.token);
      // Return user data along with success status
      return {
        success: true,
        user: response.data.user,
        token: response.data.token
      };
    }
    return response.data;
  } catch (error) {
    // Handle specific error messages from backend
    if (error.response) {
      throw new Error(
        error.response.data.message || 'Login failed. Please check your credentials.'
      );
    } else {
      throw new Error('Network error. Please try again later.');
    }
  }
};

// Logout user (clears token from localStorage)
const logout = () => {
  localStorage.removeItem('token');
};

// Get current user's profile
const getProfile = async () => {
  const token = localStorage.getItem('token');
  
  try {
    const response = await axios.get(`${API_URL}/profile`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    // Handle specific error messages from backend
    if (error.response) {
      throw new Error(
        error.response.data.message || 'Failed to fetch profile. Please login again.'
      );
    } else {
      throw new Error('Network error. Please try again later.');
    }
  }
};

// Update user profile
const updateProfile = async (userData) => {
  const token = localStorage.getItem('token');
  
  try {
    const response = await axios.put(`${API_URL}/profile`, userData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    // Handle specific error messages from backend
    if (error.response) {
      throw new Error(
        error.response.data.message || 'Failed to update profile. Please try again.'
      );
    } else {
      throw new Error('Network error. Please try again later.');
    }
  }
};

const authService = {
  register,
  login,
  logout,
  getProfile,
  updateProfile,
};

export default authService;