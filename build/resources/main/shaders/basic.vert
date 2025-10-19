#version 330 core
layout(location=0) in vec3 aPos;
layout(location=1) in vec3 aLight;
layout(location=2) in vec2 aUV;

uniform mat4 uMVP;

out vec2 vUV;
out float vLight;

void main() {
    gl_Position = uMVP * vec4(aPos, 1.0);
    vUV = aUV;
    vLight = aLight.r;
}
