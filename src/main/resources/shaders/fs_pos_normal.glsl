#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D scene;

void main()
{
    fragColor = texture(scene, fragTexCoords).rgba;
}
