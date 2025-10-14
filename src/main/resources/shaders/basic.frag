#version 330 core
in vec3 vColor;
in vec2 vUV;
out vec4 FragColor;

uniform sampler2D uTex0;

void main() {
    FragColor = texture(uTex0, vUV) * vec4(vColor, 1.0);
}
