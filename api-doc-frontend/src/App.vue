<script setup>
import { ref, computed, onMounted } from 'vue'

const apiData = ref(null)
const selectedMethod = ref('ALL')
const searchText = ref('')
const openEndpoint = ref(null)
const loading = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    const response = await fetch('/api-documentation.json')

    if (!response.ok) {
      throw new Error('Failed to load API documentation')
    }

    apiData.value = await response.json()
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
})

const filteredEndpoints = computed(() => {
  if (!apiData.value) return []

  return apiData.value.endpoints.filter((item) => {
    const methodMatch =
      selectedMethod.value === 'ALL' ||
      item.httpMethod === selectedMethod.value

    const search = searchText.value.toLowerCase().trim()

    const searchMatch =
      !search ||
      item.endpoint.toLowerCase().includes(search) ||
      item.description.toLowerCase().includes(search) ||
      item.controller.toLowerCase().includes(search) ||
      item.methodName.toLowerCase().includes(search)

    return methodMatch && searchMatch
  })
})

function toggleEndpoint(index) {
  openEndpoint.value =
    openEndpoint.value === index ? null : index
}

function methodClass(method) {
  return method.toLowerCase()
}

function scrollToEndpoints() {
  document
    .getElementById('endpoints')
    ?.scrollIntoView({ behavior: 'smooth' })
}

function cleanBaseUrl(url) {
  if (!url) return ''

  return url
    .replace('[', '')
    .replace('](http://localhost:8080)', '')
}
</script>

<template>
  <div class="app">

    <!-- ================= SIDEBAR ================= -->
    <aside class="sidebar">

      <div class="logo">
        <div class="logo-icon">R</div>

        <div>
          <h2>Rental API</h2>
          <span>Documentation</span>
        </div>
      </div>

      <nav>
        <a href="#overview">Overview</a>
        <a href="#endpoints">Endpoints</a>
        <a href="#project">Project Info</a>
      </nav>

      <div class="sidebar-bottom">
        <span>Generated automatically</span>
        <strong>JSON API Docs</strong>
      </div>

    </aside>


    <!-- ================= MAIN ================= -->
    <main class="main">

      <!-- ================= HERO ================= -->
      <section id="overview" class="hero">

        <div class="hero-content">

          <div class="small-label">
            API DOCUMENTATION
          </div>

          <h1>
            {{ apiData?.project?.name || 'Rental Website Backend API' }}
          </h1>

          <p>
            {{ apiData?.project?.description }}
          </p>

          <div class="base-url">

            <span>BASE URL</span>

            <code>
              {{
                cleanBaseUrl(
                  apiData?.project?.baseUrl
                )
              }}
            </code>

          </div>

        </div>

        <button
          class="view-button"
          @click="scrollToEndpoints"
        >
          View Endpoints →
        </button>

      </section>


      <!-- ================= STATISTICS ================= -->
      <section
        v-if="apiData"
        class="stats"
      >

        <div class="stat-card">
          <span>Endpoints</span>
          <strong>
            {{ apiData.statistics.endpoints }}
          </strong>
        </div>

        <div class="stat-card">
          <span>Controllers</span>
          <strong>
            {{ apiData.statistics.controllers }}
          </strong>
        </div>

        <div class="stat-card">
          <span>DTOs</span>
          <strong>
            {{ apiData.statistics.dtos }}
          </strong>
        </div>

        <div class="stat-card">
          <span>Entities</span>
          <strong>
            {{ apiData.statistics.entities }}
          </strong>
        </div>

        <div class="stat-card">
          <span>Services</span>
          <strong>
            {{ apiData.statistics.services }}
          </strong>
        </div>

        <div class="stat-card">
          <span>Repositories</span>
          <strong>
            {{ apiData.statistics.repositories }}
          </strong>
        </div>

      </section>


      <!-- ================= ENDPOINTS ================= -->
      <section
        id="endpoints"
        class="endpoint-section"
      >

        <div class="section-header">

          <div>

            <div class="small-label">
              API REFERENCE
            </div>

            <h2>Endpoints</h2>

            <p v-if="apiData">
              {{ filteredEndpoints.length }}
              endpoint(s) found
            </p>

          </div>


          <!-- SEARCH -->
          <div class="search-box">

            <span>⌕</span>

            <input
              v-model="searchText"
              type="text"
              placeholder="Search endpoints..."
            />

          </div>

        </div>


        <!-- ================= FILTERS ================= -->
        <div class="filters">

          <button
            :class="{ active: selectedMethod === 'ALL' }"
            @click="selectedMethod = 'ALL'"
          >
            ALL
          </button>

          <button
            class="get-filter"
            :class="{ active: selectedMethod === 'GET' }"
            @click="selectedMethod = 'GET'"
          >
            GET
          </button>

          <button
            class="post-filter"
            :class="{ active: selectedMethod === 'POST' }"
            @click="selectedMethod = 'POST'"
          >
            POST
          </button>

          <button
            class="put-filter"
            :class="{ active: selectedMethod === 'PUT' }"
            @click="selectedMethod = 'PUT'"
          >
            PUT
          </button>

          <button
            class="delete-filter"
            :class="{ active: selectedMethod === 'DELETE' }"
            @click="selectedMethod = 'DELETE'"
          >
            DELETE
          </button>

        </div>


        <!-- ================= LOADING ================= -->
        <div
          v-if="loading"
          class="message"
        >
          Loading API documentation...
        </div>


        <!-- ================= ERROR ================= -->
        <div
          v-else-if="error"
          class="message error"
        >
          {{ error }}
        </div>


        <!-- ================= ENDPOINT LIST ================= -->
        <div
          v-else
          class="endpoint-list"
        >

          <div
            v-for="(item, index) in filteredEndpoints"
            :key="index"
            class="endpoint-card"
            :class="{
              expanded: openEndpoint === index
            }"
          >

            <!-- ENDPOINT HEADER -->
            <div
              class="endpoint-header"
              @click="toggleEndpoint(index)"
            >

              <span
                class="method"
                :class="methodClass(item.httpMethod)"
              >
                {{ item.httpMethod }}
              </span>

              <code class="endpoint-path">
                {{ item.endpoint }}
              </code>

              <span class="endpoint-description">
                {{ item.description }}
              </span>

              <span class="arrow">
                {{
                  openEndpoint === index
                    ? '▲'
                    : '▼'
                }}
              </span>

            </div>


            <!-- ================= DETAILS ================= -->
            <div
              v-if="openEndpoint === index"
              class="endpoint-details"
            >

              <!-- ================= BASIC INFO ================= -->
              <div class="info-grid">

                <div>
                  <label>CONTROLLER</label>

                  <strong>
                    {{ item.controller }}
                  </strong>
                </div>

                <div>
                  <label>METHOD NAME</label>

                  <strong>
                    {{ item.methodName }}
                  </strong>
                </div>

                <div>
                  <label>TYPE</label>

                  <strong>
                    {{ item.type }}
                  </strong>
                </div>

              </div>


              <!-- ================= PARAMETERS ================= -->
              <div
                v-if="
                  item.parameters &&
                  item.parameters.length
                "
                class="detail-block"
              >

                <h3>Parameters</h3>

                <div class="parameter-list">

                  <div
                    v-for="(param, pIndex) in item.parameters"
                    :key="pIndex"
                    class="parameter"
                  >

                    <div>

                      <code>
                        {{ param.name }}
                      </code>

                      <span
                        v-if="param.required"
                        class="required"
                      >
                        required
                      </span>

                    </div>

                    <span>
                      {{ param.type }}
                    </span>

                    <span>
                      {{ param.location }}
                    </span>

                  </div>

                </div>

              </div>


              <!-- ================= REQUEST BODY ================= -->
              <div
                v-if="item.requestBody"
                class="detail-block"
              >

                <h3>Request Body</h3>

                <div class="code-box">

                  <div class="code-title">

                    <span>Type</span>

                    <strong>
                      {{ item.requestBody.type }}
                    </strong>

                  </div>


                  <div class="code-title">

                    <span>Content Type</span>

                    <code>
                      {{ item.requestBody.contentType }}
                    </code>

                  </div>


                  <!-- REQUEST FIELDS -->
                  <div
                    v-if="
                      item.requestBody.fields &&
                      item.requestBody.fields.length
                    "
                    class="fields"
                  >

                    <div class="fields-header">
                      <span>Field</span>
                    </div>

                    <div
                      v-for="(field, fIndex) in item.requestBody.fields"
                      :key="fIndex"
                      class="field-row"
                    >

                      <code>
                        {{ field }}
                      </code>

                    </div>

                  </div>

                </div>

              </div>


              <!-- ================= RESPONSE ================= -->
              <div
                v-if="item.response"
                class="detail-block"
              >

                <h3>Response</h3>

                <div class="response-box">

                  <div class="response-top">

                    <div>

                      <span>Status</span>

                      <strong class="status">
                        {{ item.response.status }}
                      </strong>

                    </div>


                    <div>

                      <span>Return Type</span>

                      <strong>
                        {{ item.response.returnType }}
                      </strong>

                    </div>


                    <div>

                      <span>Content Type</span>

                      <code>
                        {{ item.response.contentType }}
                      </code>

                    </div>

                  </div>


                  <!-- RESPONSE FIELDS -->
                  <div
                    v-if="
                      item.response.fields &&
                      item.response.fields.length
                    "
                    class="fields"
                  >

                    <div class="fields-header">
                      <span>Field</span>
                    </div>

                    <div
                      v-for="(field, fIndex) in item.response.fields"
                      :key="fIndex"
                      class="field-row"
                    >

                      <code>
                        {{ field }}
                      </code>

                    </div>

                  </div>

                </div>

              </div>

            </div>

          </div>


          <!-- NO RESULT -->
          <div
            v-if="filteredEndpoints.length === 0"
            class="no-result"
          >
            No endpoints found.
          </div>

        </div>

      </section>


      <!-- ================= PROJECT INFORMATION ================= -->
      <section
        v-if="apiData"
        id="project"
        class="project-section"
      >

        <div class="small-label">
          PROJECT INFORMATION
        </div>

        <h2>
          Project Information
        </h2>

        <div class="project-grid">

          <div>
            <span>Java Files</span>

            <strong>
              {{ apiData.statistics.javaFiles }}
            </strong>
          </div>

          <div>
            <span>Parser Classes</span>

            <strong>
              {{ apiData.statistics.parserClasses }}
            </strong>
          </div>

          <div>
            <span>Relationships</span>

            <strong>
              {{ apiData.statistics.relationships }}
            </strong>
          </div>

          <div>
            <span>Documentation Format</span>

            <strong>
              {{ apiData.generator.format }}
            </strong>
          </div>

        </div>

      </section>


      <!-- ================= FOOTER ================= -->
      <footer>
        {{ apiData?.generator?.name }}
      </footer>

    </main>

  </div>
</template>


<style scoped>

* {
  box-sizing: border-box;
}

html {
  scroll-behavior: smooth;
}

.app {
  min-height: 100vh;
  background: #f6f8fb;
  color: #172033;

  font-family:
    Inter,
    ui-sans-serif,
    system-ui,
    -apple-system,
    BlinkMacSystemFont,
    "Segoe UI",
    sans-serif;
}


/* ================= SIDEBAR ================= */

.sidebar {
  position: fixed;

  left: 0;
  top: 0;

  width: 245px;
  height: 100vh;

  background: #111827;
  color: white;

  padding: 28px 20px;

  display: flex;
  flex-direction: column;
}

.logo {
  display: flex;
  align-items: center;

  gap: 12px;
  padding: 5px;
}

.logo-icon {
  width: 42px;
  height: 42px;

  border-radius: 10px;

  background: #2563eb;

  display: flex;
  justify-content: center;
  align-items: center;

  font-size: 20px;
  font-weight: 800;
}

.logo h2 {
  margin: 0;
  font-size: 17px;
}

.logo span {
  color: #9ca3af;
  font-size: 12px;
}

.sidebar nav {
  margin-top: 50px;

  display: flex;
  flex-direction: column;

  gap: 8px;
}

.sidebar nav a {
  color: #cbd5e1;

  text-decoration: none;

  padding: 12px 14px;

  border-radius: 8px;

  font-size: 14px;
}

.sidebar nav a:hover {
  background: #1f2937;
  color: white;
}

.sidebar-bottom {
  margin-top: auto;

  border-top: 1px solid #293241;

  padding: 18px 5px 5px;

  display: flex;
  flex-direction: column;

  gap: 5px;

  font-size: 11px;
  color: #9ca3af;
}

.sidebar-bottom strong {
  color: #e5e7eb;
}


/* ================= MAIN ================= */

.main {
  margin-left: 245px;

  min-height: 100vh;
}


/* ================= HERO ================= */

.hero {
  background: white;

  padding: 65px 7%;

  border-bottom: 1px solid #e5e7eb;

  display: flex;

  justify-content: space-between;
  align-items: center;

  gap: 40px;
}

.hero h1 {
  font-size: 38px;

  margin: 12px 0;

  letter-spacing: -1px;
}

.hero p {
  max-width: 700px;

  color: #667085;

  line-height: 1.7;
}

.small-label {
  color: #2563eb;

  font-size: 11px;

  font-weight: 800;

  letter-spacing: 1.5px;
}

.base-url {
  margin-top: 24px;

  display: flex;
  align-items: center;

  gap: 12px;
}

.base-url span {
  font-size: 11px;

  font-weight: 800;

  color: #6b7280;
}

.base-url code {
  background: #f1f5f9;

  padding: 8px 12px;

  border-radius: 6px;

  color: #334155;
}

.view-button {
  border: none;

  background: #2563eb;

  color: white;

  padding: 13px 20px;

  border-radius: 8px;

  cursor: pointer;

  font-weight: 700;
}

.view-button:hover {
  background: #1d4ed8;
}


/* ================= STATISTICS ================= */

.stats {
  padding: 28px 7%;

  display: grid;

  grid-template-columns:
    repeat(6, 1fr);

  gap: 14px;
}

.stat-card {
  background: white;

  border: 1px solid #e5e7eb;

  border-radius: 10px;

  padding: 20px;
}

.stat-card span {
  display: block;

  color: #6b7280;

  font-size: 12px;
}

.stat-card strong {
  display: block;

  margin-top: 8px;

  font-size: 27px;
}


/* ================= ENDPOINT SECTION ================= */

.endpoint-section {
  padding: 35px 7%;
}

.section-header {
  display: flex;

  justify-content: space-between;

  align-items: end;

  gap: 25px;
}

.section-header h2,
.project-section h2 {
  margin: 8px 0;

  font-size: 28px;
}

.section-header p {
  margin: 0;

  color: #6b7280;

  font-size: 14px;
}


/* ================= SEARCH ================= */

.search-box {
  width: 290px;
  height: 42px;

  background: white;

  border: 1px solid #dbe0e6;

  border-radius: 8px;

  display: flex;

  align-items: center;

  padding: 0 12px;

  gap: 8px;
}

.search-box span {
  color: #64748b;

  font-size: 20px;
}

.search-box input {
  border: none;

  outline: none;

  width: 100%;

  font-size: 13px;
}


/* ================= FILTERS ================= */

.filters {
  display: flex;

  gap: 8px;

  margin: 25px 0;
}

.filters button {
  border: 1px solid #dbe0e6;

  background: white;

  padding: 8px 15px;

  border-radius: 6px;

  font-size: 11px;

  font-weight: 800;

  cursor: pointer;
}

.filters button.active {
  background: #111827;

  color: white;

  border-color: #111827;
}

.filters .get-filter.active {
  background: #15803d;

  border-color: #15803d;
}

.filters .post-filter.active {
  background: #2563eb;

  border-color: #2563eb;
}

.filters .put-filter.active {
  background: #ca8a04;

  border-color: #ca8a04;
}

.filters .delete-filter.active {
  background: #dc2626;

  border-color: #dc2626;
}


/* ================= ENDPOINT CARD ================= */

.endpoint-list {
  display: flex;

  flex-direction: column;

  gap: 10px;
}

.endpoint-card {
  background: white;

  border: 1px solid #e2e6eb;

  border-radius: 9px;

  overflow: hidden;

  transition: 0.2s;
}

.endpoint-card:hover {
  border-color: #cbd5e1;
}

.endpoint-card.expanded {
  border-color: #93c5fd;
}

.endpoint-header {
  min-height: 65px;

  padding: 13px 18px;

  display: flex;

  align-items: center;

  gap: 13px;

  cursor: pointer;
}


/* ================= METHOD ================= */

.method {
  width: 65px;

  text-align: center;

  padding: 6px 8px;

  border-radius: 5px;

  font-size: 11px;

  font-weight: 900;
}

.method.get {
  background: #dcfce7;
  color: #15803d;
}

.method.post {
  background: #dbeafe;
  color: #1d4ed8;
}

.method.put {
  background: #fef3c7;
  color: #a16207;
}

.method.delete {
  background: #fee2e2;
  color: #dc2626;
}

.endpoint-path {
  font-size: 13px;

  font-weight: 700;

  color: #111827;
}

.endpoint-description {
  color: #6b7280;

  font-size: 13px;

  flex: 1;
}

.arrow {
  color: #64748b;

  font-size: 11px;
}


/* ================= DETAILS ================= */

.endpoint-details {
  border-top: 1px solid #e5e7eb;

  padding: 25px;

  background: #fafbfc;
}


/* ================= INFO GRID ================= */

.info-grid {
  display: grid;

  grid-template-columns:
    repeat(3, 1fr);

  gap: 15px;

  margin-bottom: 25px;
}

.info-grid > div {
  background: white;

  border: 1px solid #e5e7eb;

  border-radius: 7px;

  padding: 14px;
}

.info-grid label {
  display: block;

  font-size: 10px;

  color: #64748b;

  font-weight: 800;

  margin-bottom: 7px;
}

.info-grid strong {
  font-size: 13px;
}


/* ================= DETAIL BLOCK ================= */

.detail-block {
  margin-top: 25px;
}

.detail-block h3 {
  font-size: 15px;

  margin-bottom: 12px;
}


/* ================= PARAMETERS ================= */

.parameter-list {
  background: white;

  border: 1px solid #e5e7eb;

  border-radius: 7px;

  overflow: hidden;
}

.parameter {
  display: grid;

  grid-template-columns:
    2fr 1fr 1fr;

  gap: 15px;

  padding: 12px 15px;

  border-bottom: 1px solid #eef0f3;

  font-size: 12px;
}

.parameter:last-child {
  border-bottom: none;
}

.parameter code {
  color: #7c3aed;

  font-weight: 700;
}

.required {
  margin-left: 8px;

  font-size: 9px;

  color: #dc2626;
}


/* ================= REQUEST / RESPONSE BOX ================= */

.code-box,
.response-box {
  background: white;

  border: 1px solid #e5e7eb;

  border-radius: 7px;

  padding: 17px;
}

.code-title {
  display: flex;

  gap: 10px;

  margin-bottom: 10px;

  font-size: 12px;
}

.code-title span {
  color: #64748b;

  width: 100px;
}


/* ================= FIELDS ================= */

.fields {
  margin-top: 15px;

  border-top: 1px solid #eef0f3;

  padding-top: 10px;
}

.fields-header {
  color: #64748b;

  font-size: 10px;

  font-weight: 800;

  text-transform: uppercase;

  margin-bottom: 5px;
}

.field-row {
  display: flex;

  justify-content: flex-start;

  padding: 8px 4px;

  border-bottom: 1px solid #f1f5f9;

  font-size: 12px;
}

.field-row:last-child {
  border-bottom: none;
}

.field-row code {
  color: #7c3aed;

  font-weight: 700;
}


/* ================= RESPONSE ================= */

.response-top {
  display: grid;

  grid-template-columns:
    1fr 1fr 1fr;

  gap: 20px;
}

.response-top span {
  display: block;

  color: #64748b;

  font-size: 10px;

  margin-bottom: 7px;
}

.response-top strong {
  font-size: 13px;
}

.status {
  color: #15803d;
}


/* ================= PROJECT ================= */

.project-section {
  margin: 30px 7%;

  padding: 35px;

  background: white;

  border: 1px solid #e5e7eb;

  border-radius: 10px;
}

.project-grid {
  margin-top: 25px;

  display: grid;

  grid-template-columns:
    repeat(4, 1fr);

  gap: 15px;
}

.project-grid div {
  background: #f8fafc;

  padding: 17px;

  border-radius: 7px;
}

.project-grid span {
  display: block;

  color: #64748b;

  font-size: 11px;
}

.project-grid strong {
  display: block;

  margin-top: 7px;

  font-size: 18px;
}


/* ================= FOOTER ================= */

footer {
  text-align: center;

  color: #94a3b8;

  font-size: 11px;

  padding: 40px;
}


/* ================= MESSAGE ================= */

.message,
.no-result {
  background: white;

  border: 1px solid #e5e7eb;

  padding: 30px;

  border-radius: 8px;

  text-align: center;
}

.error {
  color: #dc2626;
}


/* ================= RESPONSIVE ================= */

@media (max-width: 1100px) {

  .stats {
    grid-template-columns:
      repeat(3, 1fr);
  }

  .project-grid {
    grid-template-columns:
      repeat(2, 1fr);
  }

}


@media (max-width: 800px) {

  .sidebar {
    position: static;

    width: 100%;

    height: auto;
  }

  .sidebar nav {
    margin-top: 20px;
  }

  .sidebar-bottom {
    display: none;
  }

  .main {
    margin-left: 0;
  }

  .hero {
    flex-direction: column;

    align-items: flex-start;
  }

  .section-header {
    flex-direction: column;

    align-items: stretch;
  }

  .search-box {
    width: 100%;
  }

  .stats {
    grid-template-columns:
      repeat(2, 1fr);
  }

  .info-grid,
  .response-top {
    grid-template-columns: 1fr;
  }

  .endpoint-header {
    flex-wrap: wrap;
  }

  .endpoint-description {
    width: 100%;

    flex-basis: 100%;
  }

}


@media (max-width: 500px) {

  .stats {
    grid-template-columns: 1fr;
  }

  .project-grid {
    grid-template-columns: 1fr;
  }

  .hero h1 {
    font-size: 28px;
  }

  .endpoint-section,
  .hero {
    padding-left: 20px;

    padding-right: 20px;
  }

}

</style>