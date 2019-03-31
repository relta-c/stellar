#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D scene;

void main()
{
    fragColor = vec4(1.0 - texture(scene, fragTexCoords).rgb, texture(scene, fragTexCoords).a);
}
