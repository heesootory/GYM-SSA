<template>
  <div class="row" @click="moveDetail">
    <div class="card">
      <img
        class="video_image"
        :src="`https://img.youtube.com/vi/${video.v_id}/0.jpg`"
      />
      <div class="title">{{ video.title }}</div>
      <span><b style="font-size: 23px">&#174;</b> {{ video.v_writer }}</span>
      조회수:{{ video.view_cnt }}
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchVideoResultRow",
  props: {
    video: Object,
  },
  methods: {
    moveDetail() {
      this.$router.push({
        name: "VideoDetail",
        params: { id: this.video.v_id },
      });
    },
  },
};
</script>

<style scoped>
div {
  cursor: pointer;
}

.row {
  border: 1px solid black;
}

.video_image {
  /* width: 250px; */
  height: 250px;
}

*,
*::before,
*::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

img {
  width: 100%;
  object-fit: cover;
  padding: 0.35em;
  border-radius: 1rem;
  min-height: 10rem;
  margin-left: auto;
  margin-right: auto;
}

body {
  background: linear-gradient(45deg, #f8e8b5, #e2c4ed);
}

.container {
  display: flex;
  justify-content: center;
  max-width: clamp(30rem, 100% - 2rem, 60rem);
  width: max(22.5rem, 100%);
  min-height: 100vh;
  margin-inline: auto;
}

.container__content--flow {
  padding-block: 1rem;
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.container__content--flow > * {
  flex-grow: 1;
  margin: 0 auto;
  flex-basis: calc((30rem - 100%) * 999);
  min-width: 15rem;
  max-width: calc(100% / 1.5);
}

.card {
  display: flex;
  flex-direction: column;
  background-color: #46475c;
  border-radius: 0.5em;
  box-shadow: 0 0.125em 0.25em #0004;
  transition: all 400ms ease;
  position: relative;
}

.card::before {
  content: "";
  position: absolute;
  inset: 0;
  opacity: 0;
  border-radius: inherit;
  transition: opacity 300ms ease;
  box-shadow: 0 0.125em 0.25em #0003, 0 0.65em 0.85em #0003,
    0 0.96em 1.35em #0002;
}

.card:hover::before {
  opacity: 1;
}

.card h2 {
  padding-block: 1rem;
  text-align: center;
  color: #3871a0;
  font-weight: bolder;
  font-size: 1.37rem;
}

.card > p {
  padding-block-end: 0.5rem;
  margin-inline: 0.5em;
  text-align: center;
}

.card:nth-child(odd) {
  flex: 1 1 25%;
}

.container__content--flow:hover .card:not(:hover) {
  background-color: #fff6;
  box-shadow: none;
  opacity: 0.8;
}

.card:hover {
  scale: 1.05;
}

@media (max-width: 830px) {
  .card:nth-child(odd) {
    flex: 1 1;
  }
}

.title {
  font-weight: bold;
  font-size: 17px;
}
</style>
