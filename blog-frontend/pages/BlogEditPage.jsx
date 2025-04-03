import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import BlogForm from '../components/BlogForm';
import useBlog from '../hooks/useBlog';

export default function BlogEditPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  const { blog, updateBlog } = useBlog(id);

  const handleSubmit = async (updatedData) => {
    await updateBlog(updatedData);
    navigate(`/blogs/${id}`);
  };

  if (!user || user.id !== blog.user.id) {
    return <div>Unauthorized</div>;
  }

  return (
    <div>
      <h2>Edit Blog</h2>
      <BlogForm initialData={blog} onSubmit={handleSubmit} />
    </div>
  );
}