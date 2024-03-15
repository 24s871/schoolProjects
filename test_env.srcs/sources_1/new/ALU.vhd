----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/06/2023 03:44:09 PM
-- Design Name: 
-- Module Name: ALU - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity ALU is
 Port (clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0);
           digits:in STD_LOGIC_VECTOR (15 downto 0);
            input : in STD_LOGIC);
end ALU;

architecture Behavioral of ALU is
signal cnt:STD_LOGIC_VECTOR(1 downto 0):=(others=>'0');
signal enable:STD_LOGIC;
signal digit1:STD_LOGIC_VECTOR(15 downto 0);
signal digit2:STD_LOGIC_VECTOR(15 downto 0);
signal digit3:STD_LOGIC_VECTOR(15 downto 0);
component MPG is
    port( input: in std_logic;
          clock:in std_logic;
          en:out std_logic);
     end component;
begin
debouncer: MPG port map( en=>enable,input=>btn(0),clock=>clk);
counter:process(clk,enable)
begin
if rising_edge(clk) then
if enable='1' then
cnt<=cnt+1;
end if;
end if;
end process;
digit1<= "000000000000"+sw(3 downto 0);
digit2<= "000000000000" +sw(7 downto 4);
digit3<="000000000" +sw(7 downto 0);

end Behavioral;
