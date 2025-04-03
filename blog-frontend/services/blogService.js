import axios from 'axios'

const API_URL = 'http://localhost:8080/api/blogs'

const getAll = async (page = 1, limit = 10) => {
  const response = await axios.get(`${API_URL}?page=${page}&limit=${limit}`)
  return response.data
}

const getById = async (id) => {
  const response = await axios.get(`${API_URL}/${id}`)
  return response.data
}

const create = async (blogData, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }
  const response = await axios.post(API_URL, blogData, config)
  return response.data
}

const update = async (id, blogData, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }
  const response = await axios.put(`${API_URL}/${id}`, blogData, config)
  return response.data
}

const remove = async (id, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }
  const response = await axios.delete(`${API_URL}/${id}`, config)
  return response.data
}

export default {
  getAll,
  getById,
  create,
  update,
  remove,
}