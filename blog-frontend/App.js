import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import Header from './components/Header'
import BlogListPage from './pages/BlogListPage'
import BlogDetailPage from './pages/BlogDetailPage'
import CreateBlogPage from './pages/CreateBlogPage'
import BlogEditPage from './pages/BlogEditPage'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import PrivateRoute from './components/PrivateRoute'

function App() {
  return (
    <Router>
      <AuthProvider>
        <Header />
        <Routes>
          <Route path="/" element={<BlogListPage />} />
          <Route path="/blogs/:id" element={<BlogDetailPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/create" element={<PrivateRoute><CreateBlogPage /></PrivateRoute>} />
          <Route path="/blogs/:id/edit" element={<PrivateRoute><BlogEditPage /></PrivateRoute>} />
        </Routes>
      </AuthProvider>
    </Router>
  )
}

export default App