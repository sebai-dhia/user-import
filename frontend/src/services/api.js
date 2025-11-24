import axios from "axios";

const API_URL = "http://localhost:8082/api/files";

export const uploadCsvFile = async (file) => {
  const formData = new FormData();
  formData.append("file", file);

  try {
    const response = await axios.post(`${API_URL}/upload`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    return response.data;
  } catch (error) {
    console.error("Error uploading CSV:", error.response?.data || error.message);
    throw error;
  }
};