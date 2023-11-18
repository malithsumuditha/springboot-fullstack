FROM arm64v8/node
ARG api_base_url
WORKDIR /app
COPY package*.json .
RUN npm i
COPY . .
RUN echo "VITE_API_BASE_URL=${api_base_url}" > .env
#RUN echo "VITE_API_BASE_URL=http://localhost:8088" > .env
EXPOSE 5173
CMD ["npm", "run", "dev"]