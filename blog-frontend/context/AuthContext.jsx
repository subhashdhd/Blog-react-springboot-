import { createContext, useState, useEffect } from 'react'
import { jwtDecode } from 'jwt-decode'
import authService from '../services/authService'

export const AuthContext = createContext()

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (token) {
      const decoded = jwtDecode(token)
      setUser(decoded)
      setIsAuthenticated(true)
    }
    setIsLoading(false)
  }, [])

  const login = async (credentials) => {
    try {
      const response = await authService.login(credentials)
      localStorage.setItem('token', response.data.token)
      const decoded = jwtDecode(response.data.token)
      setUser(decoded)
      setIsAuthenticated(true)
      return { success: true }
    } catch (error) {
      return { success: false, message: error.response.data.message }
    }
  }

  const register = async (userData) => {
    try {
      const response = await authService.register(userData)
      return { success: true }
    } catch (error) {
      return { success: false, message: error.response.data.message }
    }
  }

  const logout = () => {
    localStorage.removeItem('token')
    setUser(null)
    setIsAuthenticated(false)
  }

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, isLoading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  )
}