import { useState } from 'react';

export default function BlogForm({ initialData, onSubmit }) {
  const [formData, setFormData] = useState(initialData || {
    title: '',
    content: ''
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        value={formData.title}
        onChange={(e) => setFormData({...formData, title: e.target.value})}
        placeholder="Blog title"
        required
      />
      <textarea
        value={formData.content}
        onChange={(e) => setFormData({...formData, content: e.target.value})}
        placeholder="Blog content"
        required
      />
      <button type="submit">Save</button>
    </form>
  );
}