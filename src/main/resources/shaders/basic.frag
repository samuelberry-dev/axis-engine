#version 330 core
in vec2 vUV;
in float vLight;

out vec4 FragColor;

uniform sampler2D uTex0;

void main() {
    vec3 albedo = texture(uTex0, vUV).rgb;
    FragColor = vec4(albedo * vLight, 1.0);
}
