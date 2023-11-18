FROM node:14
ARG api_base_url
WORKDIR /app
COPY package*.json .
RUN npm install
COPY . .
RUN echo "VITE_API_BASE_URL=${api_base_url}" > .env
#RUN echo "VITE_API_BASE_URL=http://localhost:8088" > .env
EXPOSE 5173
CMD ["npm", "run", "dev"]