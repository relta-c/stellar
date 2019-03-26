#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D sprite;
uniform vec3 color;

void main()
{
    fragColor = vec4(1.0 - texture(sprite, fragTexCoords).rgb, texture(sprite, fragTexCoords).a);
}
