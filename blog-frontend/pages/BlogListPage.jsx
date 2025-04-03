import { useState } from 'react';
import BlogCard from '../components/BlogCard';
import Pagination from '../components/Pagination';
import useBlogs from '../hooks/useBlogs';

export default function BlogListPage() {
  const [page, setPage] = useState(1);
  const { blogs, totalPages, loading } = useBlogs(page);

  if (loading) return <div>Loading blogs...</div>;

  return (
    <div className="blog-list">
      <h1>All Blogs</h1>
      {blogs.map(blog => (
        <BlogCard key={blog.id} blog={blog} />
      ))}
      <Pagination 
        currentPage={page}
        totalPages={totalPages}
        onPageChange={setPage}
      />
    </div>
  );
}