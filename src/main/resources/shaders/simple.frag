#version 330 core
out vec4 FragColor;

void main() {
    // soft white so clear-color tinting is obvious if it shows
    FragColor = vec4(0.95, 0.95, 0.95, 1.0);
}
