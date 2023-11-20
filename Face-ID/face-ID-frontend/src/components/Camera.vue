<template>
  <div class="video-frame">
    <h1>摄像头流</h1>
    <video ref="videoElement" autoplay></video>
    <button @click="startCamera">启动摄像头</button>
  </div>
</template>

<script>
export default {
  name: 'CameraApp',
  methods: {
    async startCamera() {
      try {
        // 请求摄像头权限
        const stream = await navigator.mediaDevices.getUserMedia({ video: true });
        // 将视频流绑定到video元素上
        this.$refs.videoElement.srcObject = stream;
      } catch (error) {
        console.error('Error accessing the camera', error);
      }
    },
  },
};
</script>

<!-- 添加一些简单的样式 -->
<style>
.video-frame{
  display: flex;
  align-items: center;
}
.video-frame video {
  width: 500px;
  height: 500px;
  background-color: black;
}
</style>

