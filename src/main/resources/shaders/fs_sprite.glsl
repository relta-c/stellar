#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D sprite;
uniform vec3 color;
uniform float transparency;// TODO : Transparency with blur

void main()
{
    vec4 textureColor = texture(sprite, fragTexCoords);
    textureColor *= transparency;
    fragColor = vec4(color.rgb, 1.0) * textureColor;
}
