import { Link } from 'react-router-dom';

export default function BlogCard({ blog }) {
  return (
    <div className="blog-card">
      <h3>{blog.title}</h3>
      <p>{blog.content.substring(0, 100)}...</p>
      <Link to={`/blogs/${blog.id}`}>Read More</Link>
    </div>
  );
}