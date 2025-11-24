import { useState } from "react";
import { uploadCsvFile } from "../services/api";

function FileUploadForm() {
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState("");
  const [status, setStatus] = useState("");
  const [loading, setLoading] = useState(false);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setMessage("");
    setStatus("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      setMessage("Please select a CSV file.");
      setStatus("error");
      return;
    }

    setLoading(true);
    setMessage("");
    setStatus("");
    try {
      const response = await uploadCsvFile(file);
      setMessage(response);
      setStatus("success");
    } catch (error) {
      const msg = error.response?.data || error.message;
      setMessage("Failed to upload file: " + msg);
      setStatus("error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="upload-container">
      <form onSubmit={handleSubmit} className="upload-form">
        <input type="file" accept=".csv" onChange={handleFileChange} />
        <button type="submit" disabled={loading}>
          {loading ? "Uploading..." : "Upload CSV"}
        </button>
      </form>
      {message && (
        <p className={`upload-message ${status === "success" ? "success" : "error"}`}>
          {message}
        </p>
      )}
    </div>
  );
}

export default FileUploadForm;