import { useParams } from 'react-router-dom';
import useBlog from '../hooks/useBlog';

export default function BlogDetailPage() {
  const { id } = useParams();
  const { blog, loading, error } = useBlog(id);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="blog-detail">
      <h1>{blog.title}</h1>
      <p>{blog.content}</p>
      <p>Posted on: {new Date(blog.createdAt).toLocaleDateString()}</p>
    </div>
  );
}