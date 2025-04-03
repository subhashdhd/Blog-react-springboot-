import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import BlogForm from '../components/BlogForm';
import useCreateBlog from '../hooks/useCreateBlog';

export default function CreateBlogPage() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const { createBlog } = useCreateBlog();

  const handleSubmit = async (blogData) => {
    await createBlog(blogData);
    navigate('/');
  };

  if (!user) {
    return <div>Please login to create a blog</div>;
  }

  return (
    <div>
      <h2>Create New Blog</h2>
      <BlogForm onSubmit={handleSubmit} />
    </div>
  );
}